<script lang="ts">
	import { onMount } from 'svelte';
	import type { Account } from '$lib';

	let accounts: Account[] = $state([]);
	let loading: boolean = $state(true);

	const fetchAccounts = async () => {
		const res = await fetch('http://localhost:8080/api/accounts'); // Adjust URL based on backend
		loading = false;
		accounts = await res.json();
	};

	onMount(fetchAccounts);
</script>

<h1 class="font-title text-4xl">Accounts</h1>
<div class="font-body">
	{#if !loading}
		<div class="flex justify-end pb-2 text-sm">
			<button class="cursor-pointer bg-black px-2.5 py-1.5 text-white">+ Add account</button>
		</div>
		<div class="flex flex-col">
			{#each accounts as account}
				<a href="/accounts/{account.id}" class="flex cursor-pointer p-2 hover:bg-gray-100">
					<div class="flex-1">{account.name}</div>
					<div class:text-red-500={account.balance < 0} class:text-green-500={account.balance > 0}>
						${account.balance}
					</div>
				</a>
			{/each}
		</div>
	{:else}
		<div>loading...</div>
	{/if}
</div>
