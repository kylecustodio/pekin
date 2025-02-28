<script lang="ts">
	import { onMount } from 'svelte';
	import type { Transaction } from '$lib';
	import type { PageProps } from '../../../../.svelte-kit/types/src/routes/$types';

	let { data }: PageProps = $props();

	let loading: boolean = $state(true);
	let transactions: Transaction[] = $state([]);

	const fetchTransactions = async () => {
		const res = await fetch(`http://localhost:8080/api/transactions/account/${data.account.id}`); // Adjust URL based on backend
		loading = false;
		transactions = await res.json();
	};

	onMount(fetchTransactions());
</script>

<div class="flex items-center gap-4 pb-4">
	<h1 class="font-title text-4xl">{data.account.name}</h1>
	<div
		class="font-body"
		class:text-red-500={data.account.balance < 0}
		class:text-green-500={data.account.balance > 0}
	>
		${data.account.balance}
	</div>
</div>
<div class="font-body">
	{#if !loading}
		{#each transactions as transaction}
			<div class="flex gap-4">
				<div>${transaction.amount}</div>
				<div>{transaction.description ?? '-'}</div>
				<div>{transaction.date ?? '-'}</div>
			</div>
		{/each}
	{:else}
		<div>loading...</div>
	{/if}
</div>
