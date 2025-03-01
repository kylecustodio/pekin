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

	onMount(fetchTransactions);
</script>

<div class="flex items-center gap-4">
	<h1 class="font-body">{data.account.name}</h1>
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
		<div class="flex justify-end text-sm pb-2">
			<!-- TODO: Filtering and sorting options -->
			<button class="cursor-pointer bg-black px-2.5 py-1.5 text-white ">+ New</button>
		</div>
		<div class="flex flex-col">
			{#each transactions as transaction}
				<div class="flex items-center p-2 hover:bg-gray-100">
					<div class="flex flex-1 flex-col gap-0.5">
						<div>{transaction.description ?? '-'}</div>
						<div class="text-xs text-gray-500">{transaction.date ?? '-'}</div>
					</div>
					<div
						class:text-red-500={transaction.amount < 0}
						class:text-green-500={transaction.amount > 0}
					>
						${transaction.amount}
					</div>
				</div>
			{/each}
		</div>
	{:else}
		<div>loading...</div>
	{/if}
</div>
